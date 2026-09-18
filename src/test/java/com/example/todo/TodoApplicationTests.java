package com.example.todo;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class TodoApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Test
	void 정상_흐름() throws Exception {
		String response = mockMvc.perform(post("/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"과제하기\"}"))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("과제하기"))
				.andExpect(jsonPath("$.createdAt").exists())
				.andReturn().getResponse().getContentAsString();
		Number todoId = JsonPath.read(response, "$.todoId");

		mockMvc.perform(get("/todos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.todos[0].todoId").value(todoId.longValue()))
				.andExpect(jsonPath("$.todos[0].completed").value(false));

		mockMvc.perform(patch("/todos/{todoId}/completed", todoId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"completed\":true}"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.completed").value(true));

		mockMvc.perform(delete("/todos/{todoId}", todoId))
				.andExpect(status().isNoContent());
	}

	@Test
	void 빈_제목은_400을_반환한다() throws Exception {
		mockMvc.perform(post("/todos")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"name\":\"   \"}"))
				.andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.status").value(400))
				.andExpect(jsonPath("$.message").exists());
	}

	@Test
	void 없는_할_일은_404를_반환한다() throws Exception {
		mockMvc.perform(get("/todos/{todoId}", Long.MAX_VALUE))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.status").value(404))
				.andExpect(jsonPath("$.message").value("할 일을 찾을 수 없습니다."));
	}
}
