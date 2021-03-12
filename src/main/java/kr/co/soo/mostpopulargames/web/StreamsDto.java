package kr.co.soo.mostpopulargames.web;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class StreamsDto {
	
	private List<StreamerDto> data;
	private Pagination pagination;

	@Getter
	@ToString
	static final class Pagination {
		private String cursor;
	}

	@Getter
	@ToString
	static final class StreamerDto{
		private String game_id;
		@Setter
		private String game_name;
		private String id;
		private String language;
		private String started_at;
		private String thumbnail_url;
		private String title;
		private String type;
		private String user_id;
		private String user_login;
		private String user_name;
		private int viewer_count;
		private List<String> tag_ids;
	}
}


