package kr.co.soo.mostpopulargames.api;

import kr.co.soo.mostpopulargames.web.dto.IdgbGameDto;

import java.io.IOException;

public interface IgdbApiCall {
	IdgbGameDto getGameInfo(String gameName) throws IOException;
}
