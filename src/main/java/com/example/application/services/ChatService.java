package com.example.application.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import dev.hilla.BrowserCallable;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;

@BrowserCallable
@AnonymousAllowed
public class ChatService {

    @Value("${openai.api.key}")
    private String OPENAI_API_KEY;

    private Assistant assistant;

    interface Assistant {
        String chat(String message);
    }

    @PostConstruct
    public void init(){
        var memory= TokenWindowChatMemory.withMaxTokens(1000,new OpenAiTokenizer
                ("gpt-3.5-turbo"));
        assistant= AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(OPENAI_API_KEY))
                .chatMemory(memory)
                .build();
    }
    public String chat(String message){
        return assistant.chat(message);
    }
}
