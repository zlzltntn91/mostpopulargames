package kr.co.soo.mostpopulargames.web.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class IdgbGameDto {
	private int id;
	private String name;
	private int[] platforms;
	@Setter
	private List<String> platformsName;
}
