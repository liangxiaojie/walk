package com.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AppPictureService
{
	public List<HashMap<String, String>> queryAppPicture(Map<String, String> parasMap);
}