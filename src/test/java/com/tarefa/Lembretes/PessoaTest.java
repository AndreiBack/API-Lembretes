package com.tarefa.Lembretes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tarefa.Lembretes.DTO.PessoaDTO;
import com.tarefa.Lembretes.Entity.Pessoa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PessoaTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreatePessoa() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "VanderleiOBrabo");
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("Pessoa cadastrada com sucesso!", response);
    }

    @Test
    public void testGetPessoaById() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "Bob");
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pessoas")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Pessoa pessoa = objectMapper.readValue(response, Pessoa.class);
        assertEquals("VanderleiOBrabo", pessoa.getNome());
    }

    @Test
    public void testUpdatePessoa() throws Exception {

        PessoaDTO pessoaDTO = new PessoaDTO(null, "Charlie");
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk());


        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/pessoas")
                        .param("name", pessoaDTO.getNome()))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        Pessoa pessoa = objectMapper.readValue(response, Pessoa.class);
        Long pessoaId = pessoa.getId();


        PessoaDTO updatedPessoaDTO = new PessoaDTO(pessoaId, "David");
        mockMvc.perform(MockMvcRequestBuilders.put("/pessoas/{id}", pessoaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPessoaDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Pessoa editada com sucesso!"));
    }


    @Test
    public void testDeletePessoa() throws Exception {
        PessoaDTO pessoaDTO = new PessoaDTO(null, "Eve");
        mockMvc.perform(MockMvcRequestBuilders.post("/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaDTO)))
                .andExpect(status().isOk());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete("/pessoas")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("Pessoa excluida com sucesso!", response);
    }
}