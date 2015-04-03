import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class ChronoTimer {
	
	public enum Event
	{
		IND, GRP, PARIND, PARGRP
	}
	
	IStream _stream;
	ArrayList<IStream> _streams;
	int _currentRun;
	Channel _channels[];
	boolean _isOn;
	Clock clock;
	ChronoTimer(){
		_stream = new IndividualStream();
		_streams = new ArrayList<IStream>();
		_streams.add(_stream);
		_currentRun = 0;
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
			_streams.set(_currentRun, _stream);
		}
		else if(eventType.equalsIgnoreCase("GRP")){
			_stream = new GroupStream();
			_streams.set(_currentRun, _stream);
		}
		else if(eventType.equalsIgnoreCase("PARIND")){
			_stream = new IndividualParallelStream();
			_streams.set(_currentRun, _stream);
		}
		else if(eventType.equalsIgnoreCase("PARGRP")){
			_stream= new GroupParallelStream();
			_streams.set(_currentRun, _stream);
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
		if(_isOn)
		{
			_stream = new IndividualStream();
			_streams.add(_stream);
		}
	}
	
	public void endRun(){
		if(_isOn){
			_stream = null;
			_currentRun++;
		}
	}
	
	public void export(int runNumber) throws FileNotFoundException{
		Integer runNumberUp = new Integer(runNumber);
		File file = new File("C:/Users/Noah/Documents/ChronoTimerOut/RunNumber" + runNumberUp.toString() + ".txt");
		file.getParentFile().mkdirs();
		PrintWriter writer = new PrintWriter(file);
		writer.println(_streams.get(runNumber-1).toString());
		writer.close();
	}
	

	

	
}