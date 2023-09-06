package com.tarefa.Lembretes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarefa.Lembretes.Controller.PessoaController;
import com.tarefa.Lembretes.DTO.LembreteDTO;
import com.tarefa.Lembretes.DTO.PessoaDTO;
import com.tarefa.Lembretes.Entity.Lembrete;
import com.tarefa.Lembretes.Entity.Pessoa;
import com.tarefa.Lembretes.Service.LembreteService;
import com.tarefa.Lembretes.Service.PessoaService;
import org.hibernate.collection.spi.PersistentSortedMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LembreteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    PessoaService pessoaService;
    @Autowired
    LembreteService lembreteService;

    @Test
    public void testCreateLembrete() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "Cleitin");
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk());


        assertNotNull(pessoaService.findByName("Cleitin"));

        LembreteDTO lembreteDTO = new LembreteDTO(null, "Lembrete de teste", pessoaDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/lembretes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lembreteDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("Lembrete cadastrado com sucesso!", response);
    }


    @Test
    public void testGetAllLembretesByPessoa() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "Bob");
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk());

        LembreteDTO lembreteDTO = new LembreteDTO(null, "Lembrete de teste", pessoaDTO);
        mockMvc.perform(MockMvcRequestBuilders.post("/lembretes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(lembreteDTO)))
                .andExpect(status().isOk());

        assertNotNull(pessoaService.findByName("Bob"));
        assertNotNull(lembreteService.findAllOfPessoa("Bob"));

        mockMvc.perform(MockMvcRequestBuilders.get("/lembretes/list")
                        .param("name", "Bob"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].recado").value("Lembrete de teste"));
    }
}