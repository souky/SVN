package com.jy.moudles.score.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jy.moudles.score.entity.ScoreVO;
/**
 * 比较器
 * @author Administrator
 *
 */
public class CompareUtils{
	//占比比较
	public static List<ScoreVO> compareTo(List<ScoreVO> param) throws Exception{
		List<ScoreVO> list = new ArrayList<>();
		for (ScoreVO scoreVO : param) {
			list.add(scoreVO);
		}
		
		//记录自己原本的下标
		for (int i = 0; i < list.size(); i++) {
			list.get(i).setIndex(i);
		}
		
		Collections.sort(list,new Comparator<ScoreVO>(){
			@Override
			public int compare(ScoreVO scvOne, ScoreVO scvTwo) {
				String one = scvOne.getClassTotalDiffCoefficient();
				String two = scvTwo.getClassTotalDiffCoefficient();
				one = one.substring(0, one.length()-1);
				two = two.substring(0, two.length()-1);
				 //精确表示
		        BigDecimal dataOne = new BigDecimal(one);
		        BigDecimal dataTwo = new BigDecimal(two);
		        
		        //大于为1，相同为0，小于为-1
		        if (dataOne.compareTo(dataTwo) == -1) {
		        	return 1;
		        } else if (dataOne.compareTo(dataTwo) == 0) {
		        	return 0;
		        } else {
		        	return -1;
		        }
			}
		});
		
		
		list = list.subList(0, list.size()==1?1:list.size()==2?2:3);
		
		for (ScoreVO scoreVO : list) {
			param.get(scoreVO.getIndex()).setType("1");
		}
		
		return param;
	}
	
	public static void main(String[] args) throws Exception {
		List<ScoreVO> list = new ArrayList<>();
		ScoreVO s1 = new ScoreVO();
		s1.setClassTotalDiffCoefficient("9%");
		list.add(s1);
		
		ScoreVO s2 = new ScoreVO();
		s2.setClassTotalDiffCoefficient("18%");
		list.add(s2);
		
		ScoreVO s3 = new ScoreVO();
		s3.setClassTotalDiffCoefficient("4%");
		list.add(s3);
		
		ScoreVO s4 = new ScoreVO();
		s4.setClassTotalDiffCoefficient("6%");
		list.add(s4);
		
		ScoreVO s5 = new ScoreVO();
		s5.setClassTotalDiffCoefficient("5%");
		list.add(s5);
		
		ScoreVO s6= new ScoreVO();
		s6.setClassTotalDiffCoefficient("55%");
		list.add(s6);
		
		ScoreVO s7 = new ScoreVO();
		s7.setClassTotalDiffCoefficient("1%");
		list.add(s7);
		
		List<ScoreVO> param = compareTo(list);
		for (ScoreVO scoreVO : param) {
			System.out.println(scoreVO.getClassTotalDiffCoefficient()+","+scoreVO.getType());
		}
		
	}
	
}
