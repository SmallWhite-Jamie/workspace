package com.totao.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateTimeTest {
	@Test
	public void timeTest(){
		DateTime dateTime =new DateTime();
		String string = dateTime.toString("/yyy/MM/dd");
		SimpleDateFormat format =new SimpleDateFormat("/yyy/MM/dd");
		String date = format.format(new Date());
		System.out.println(string);
	}
}
