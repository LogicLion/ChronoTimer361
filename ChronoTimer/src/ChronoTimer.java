import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
public class ChronoTimer {
	
	IStream _stream;
	Channel _channels[];
	boolean _isOn;
	Clock clock;
	ChronoTimer(){
		_stream = new IndividualStream();
		_channels = new Channel[2];
		_channels[0] = new Channel();
		_channels[1] = new Channel();
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
	

	

	
}