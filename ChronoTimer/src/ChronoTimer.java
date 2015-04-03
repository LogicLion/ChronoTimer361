import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class ChronoTimer {
	

	
	
	public enum Event
	{
		IND, GRP, PARIND, PARGRP
	}
	
	IStream _stream;
	
	Channel _channels[];
	boolean _isOn;
	Clock clock;
	ChronoTimer(){
		_stream = new IndividualStream();
		_channels = new Channel[4];
		_channels[0] = new Channel();
		_channels[1] = new Channel();
		_channels[2] = new Channel();
		_channels[3] = new Channel();
		clock=Clock.systemUTC();
	}
	
	public Clock getClock(){
		return clock;
	}
	
	public void turnOn(){
		_isOn=true;
	}
	
	public void turnOff(){
		_isOn=false;
	}
	
	public void time(LocalDateTime set){
		Clock systemClock = Clock.systemDefaultZone();
		clock=Clock.offset(systemClock, Duration.between(LocalDateTime.now(), set));
		//LocalDateTime check = LocalDateTime.now(clock);
		//System.out.println(check.toString());
	}
	
	public void event(String eventType){
		if(eventType.equalsIgnoreCase("IND")){
			_stream = new IndividualStream();
		}
		else if(eventType.equalsIgnoreCase("GRP")){
			_stream = new GroupStream();
		}
		else if(eventType.equalsIgnoreCase("PARIND")){
			_stream = new IndividualParallelStream();
		}
		else if(eventType.equalsIgnoreCase("PARGRP")){
			_stream= new GroupParallelStream();
		}
	}

	public void connect(String sensorType, int channel){
		if(_isOn)
		{
		_channels[channel-1].connect(sensorType);
		}
	}
	
	public void toggle(int channel){
		if(_isOn)
		{
		_channels[channel-1].toggle();
		}
	}
	
	public void start(){
		if(_isOn)
		{
			_stream.startRecord(_channels[0].trigger(clock));
		}
	}
	
	public void end(){
		if(_isOn)
		{
			_stream.finishRecord(_channels[1].trigger(clock));	
		}
		
	}
	
	public void trigger(int channel)
	{
		if(_isOn){
			if(channel % 2 == 1){
			_stream.startRecord(_channels[channel-1].trigger(clock));
			}
			else if(channel % 2 == 0){
			_stream.finishRecord(_channels[channel-1].trigger(clock));
			}
		}
	}
	
	public void num(int runNumber)
	{
		if(_isOn)
		{
			_stream.num(runNumber);
		}
	}
	
	public void print(){
		if(_isOn)
		{
			System.out.println(_stream.toString());
		}
	}

	public void DNF() {
		if(_isOn)
		{
			_stream.DNFRecord();
		}
	}

	public void cancel() {
		if(_isOn)
		{
			_stream.DNFRecord();
		}
	}
	
	public void newRun(){
		if(_isOn){
			_stream = new IndividualStream();
		}
	}
	
	public void endRun(){
		if(_isOn){
			_stream = null;
		}
	}
	

	

	

	
}