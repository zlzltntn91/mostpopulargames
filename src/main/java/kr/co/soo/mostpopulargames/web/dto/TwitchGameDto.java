package kr.co.soo.mostpopulargames.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TwitchGameDto {
	private String game_name;
	private List<StreamsDto.StreamerDto> streamers;
	private int viewer_count;
}
