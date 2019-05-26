package com.jy.moudles.score.constant;

import java.util.HashMap;
import java.util.Map;

public class SubjectConstant {

	 public static Map<String,Object> subjectDbMap = new HashMap<String, Object>();
	 static {
        subjectDbMap.put("总分", "total_score");
        subjectDbMap.put("语文", "chinese_score");
        subjectDbMap.put("数学", "math_score");
        subjectDbMap.put("英语", "english_score");
        subjectDbMap.put("物理", "physical_score");
        subjectDbMap.put("化学", "chemical_score");
        subjectDbMap.put("生物", "biology_score");
        subjectDbMap.put("地理", "geography_score");
        subjectDbMap.put("科学", "science_score");
        subjectDbMap.put("政治", "politics_score");
        subjectDbMap.put("历史", "history_score");
        subjectDbMap.put("思想品德", "moral_score");
        subjectDbMap.put("历史与社会", "history_society_score");
        subjectDbMap.put("品德与社会", "quality_society_score");
        subjectDbMap.put("品德与生活", "quality_life_score");
        subjectDbMap.put("美术", "painting_score");
        subjectDbMap.put("艺术", "art_score");
        subjectDbMap.put("音乐", "music_score");
        subjectDbMap.put("体育", "sports_score");
	 }
	 
	 public  static Map<String,Object> subjectMap = new HashMap<String, Object>();
		static {
			subjectMap.put("总分"     , "totalScore");
			subjectMap.put("语文"     , "chineseScore");
			subjectMap.put("数学"     , "mathScore");
			subjectMap.put("英语"     , "englishScore");
			subjectMap.put("物理"     , "physicalScore");
			subjectMap.put("化学"     , "chemicalScore");
			subjectMap.put("生物"     , "biologyScore");
			subjectMap.put("地理"     , "geographyScore");
			subjectMap.put("科学"     , "scienceScore");
			subjectMap.put("政治"     , "politicsScore");
			subjectMap.put("历史"     , "historyScore");
			subjectMap.put("思想品德"  , "moralScore");
			subjectMap.put("历史与社会", "historySocietyScore");
			subjectMap.put("品德与社会", "qualitySocietyScore");
			subjectMap.put("品德与生活", "qualityLifeScore");
			subjectMap.put("美术"     , "paintingScore");
			subjectMap.put("艺术"     , "artScore");
			subjectMap.put("音乐"     , "musicScore");
			subjectMap.put("体育"     , "sportsScore");
		}
}
