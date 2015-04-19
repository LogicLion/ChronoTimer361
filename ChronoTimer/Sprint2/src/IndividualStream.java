import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class IndividualStream implements IStream {

private int _runNumber;
private Queue<TimingRecord> runs;
private Queue<TimingRecord> pendingRuns;
private ArrayList<TimingRecord> completedRuns;

public IndividualStream(int runNumber)	
{
	_runNumber = runNumber;
	runs = new LinkedList<TimingRecord>();
	pendingRuns = new LinkedList<TimingRecord>();
	completedRuns = new ArrayList<TimingRecord>();
}

public void num(int BIB)
{
	pendingRuns.add(new TimingRecord(BIB));
}

public void cancelRecord()
{
	runs.peek().cancel();
	completedRuns.add(runs.poll());
	_runNumber++;
	System.out.println("Next Racer");
}

public void startRecord(LocalDateTime start)
{
	TimingRecord current = pendingRuns.poll();
	current.start(start);
	runs.add(current);
}

public void finishRecord(LocalDateTime finish)
{
	runs.peek().finish(finish);
	completedRuns.add(runs.poll());
	_runNumber++;
		
}

public void DNFRecord()
{
	runs.peek().DNF();
	completedRuns.add(runs.poll());
	_runNumber++;
}


@Override
public String toString() 
{
	StringBuilder concat = new StringBuilder("RUN\tBIB\tTIME\n");
	for(TimingRecord i : completedRuns)
	{
		concat.append(_runNumber + "\t" + i.toString() + "\n");
	}
	return concat.toString();
}

public void finishRecord(LocalDateTime finish, int channel)
{
	return;
	
}

}