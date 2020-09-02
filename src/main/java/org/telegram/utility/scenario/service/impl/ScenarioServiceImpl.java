package org.telegram.utility.scenario.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.springframework.stereotype.Service;
import org.telegram.utility.scenario.config.ScenarioConfig;
import org.telegram.utility.scenario.entity.Keyboard;
import org.telegram.utility.scenario.entity.ReadyScenario;
import org.telegram.utility.scenario.entity.Row;
import org.telegram.utility.scenario.entity.Scenario;
import org.telegram.utility.scenario.repository.ReadyScenarioRepository;
import org.telegram.utility.scenario.repository.ScenarioRepository;
import org.telegram.utility.scenario.service.ScenarioService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileReader;
import java.util.*;

@Service
public class ScenarioServiceImpl implements ScenarioService {

    protected final ScenarioRepository scenarioRepository;
    protected final ReadyScenarioRepository readyScenarioRepository;
    protected final ScenarioConfig scenarioConfig;

    public ScenarioServiceImpl(ScenarioRepository scenarioRepository, ReadyScenarioRepository readyScenarioRepository, ScenarioConfig scenarioConfig) {
        this.scenarioRepository = scenarioRepository;
        this.readyScenarioRepository = readyScenarioRepository;
        this.scenarioConfig = scenarioConfig;
    }

    @Override
    public void uploadScenario() {
        FileReader fileReader;
        try {
            fileReader = new FileReader(scenarioConfig.getFile());
            Serializer serializer = new Persister();
            List<Scenario> list = serializer.read(Scenarios.class, fileReader).getScenarios();

            scenarioRepository.saveAll(list);
            readyScenarioRepository.saveAll(serializationAll(list));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public Scenario getScenarioByKey(String key) {
        return scenarioRepository.findById(key).orElseThrow();
    }

    @Override
    public void updScenario(Scenario scenario) {
        scenarioRepository.save(scenario);

        SendMessage message = buildMessageByScenarioKey(scenario.getKey());
        readyScenarioRepository.save(new ReadyScenario(scenario.getKey(), serialization(message)));
    }

    @Override
    public SendMessage getMessageByScenarioKey(String key) {
        SendMessage sendMessage = deserialization(readyScenarioRepository.findById(key)
                .map(ReadyScenario::getDeserialization).orElse(null));

        if (sendMessage == null) {
            sendMessage = buildMessageByScenarioKey(key);
        }

        return sendMessage;
    }

    private List<ReadyScenario> serializationAll(List<Scenario> scenarios) {
        List<ReadyScenario> list = new LinkedList<>();
        scenarios.forEach(scenario -> {
            SendMessage message = buildMessageByScenarioKey(scenario.getKey());
            String serialization = serialization(message);
            list.add(new ReadyScenario(scenario.getKey(), serialization));
        });
        return list;
    }

    private String serialization(SendMessage sendMessage) {
        String json;
        try {
            json = new ObjectMapper().writeValueAsString(sendMessage);
        } catch (JsonProcessingException e) {
            json = null;
        }
        return json;
    }

    private SendMessage deserialization(String json) {
        SendMessage sendMessage;
        try {
            sendMessage = new ObjectMapper().readValue(json, SendMessage.class);
        } catch (JsonProcessingException e) {
            sendMessage = null;
        }
        return sendMessage;
    }

    private SendMessage buildMessageByScenarioKey(String scenarioKey) {
        SendMessage sendMessage = new SendMessage();
        scenarioRepository.findById(scenarioKey).ifPresent(scenario -> {
            sendMessage.setText(scenario.getText().getText());
            sendMessage.setParseMode(scenario.getText().getParseMode().name());
            sendMessage.setReplyMarkup(buildKeyboard(scenario.getKeyboard()));
        });
        return sendMessage;
    }

    private ReplyKeyboard buildKeyboard(Keyboard keyboard) {
        ReplyKeyboard replyKeyboard = null;
        if (keyboard.getType() != null) {
            replyKeyboard = switch (keyboard.getType()) {
                case REPLY -> buildReply(keyboard.getRows());
                case INLINE -> buildInline(keyboard.getRows());
            };
        }
        return replyKeyboard;
    }

    private ReplyKeyboardMarkup buildReply(List<Row> rows) {
        List<KeyboardRow> keyboardRows = new LinkedList<>();
        rows.stream().distinct().forEach(row -> {
            KeyboardRow keyboardRow = new KeyboardRow();
            row.getButtons().stream().distinct().forEach(button -> {
                keyboardRow.add(button.getTitle());
            });
            keyboardRows.add(keyboardRow);
        });
        return new ReplyKeyboardMarkup(keyboardRows).setOneTimeKeyboard(true).setResizeKeyboard(true);
    }

    private InlineKeyboardMarkup buildInline(List<Row> rows) {
        List<List<InlineKeyboardButton>> keyboardButtons = new LinkedList<>();
        rows.stream().distinct().forEach(row -> {
            List<InlineKeyboardButton> keyboardButton = new LinkedList<>();
            row.getButtons().stream().distinct().forEach(button -> {
                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(button.getTitle());
                switch (button.getType()) {
                    case CALLBACK_DATA -> inlineKeyboardButton.setCallbackData(button.getData());
                    case URL -> inlineKeyboardButton.setUrl(button.getData());
                }
                keyboardButton.add(inlineKeyboardButton);
            });
            keyboardButtons.add(keyboardButton);
        });
        return new InlineKeyboardMarkup(keyboardButtons);
    }

    private static class Scenarios {
        @ElementList(inline = true, required = false)
        private List<Scenario> scenarios;

        public Scenarios() {
        }

        public Scenarios(List<Scenario> scenarios) {
            this.scenarios = scenarios;
        }

        public void setScenarios(List<Scenario> scenarios) {
            this.scenarios = scenarios;
        }

        public List<Scenario> getScenarios() {
            return scenarios;
        }
    }

}
