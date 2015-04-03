import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GroupParallelStream implements IStream {

private int _runNumber;
private LocalDateTime globalStart;
private Queue<TimingRecord> runs;
private Queue<TimingRecord> pendingRuns;
private ArrayList<TimingRecord> completedRuns;

public GroupParallelStream(int runNumber)	
{
	_runNumber = runNumber;
	runs = new LinkedList<TimingRecord>();
	pendingRuns = new LinkedList<TimingRecord>();
	completedRuns = new ArrayList<TimingRecord>();
}

public void num(int BIB)
{
	completedRuns.add(new TimingRecord(BIB));
}

public void cancelRecord()
{
	runs.peek().cancel();
	completedRuns.add(runs.poll());
}

public void startRecord(LocalDateTime start)
{
	globalStart=start;
}


public void finishRecord(LocalDateTime finish, int channel)
{
	int index = channel/2 - 1;
	completedRuns.get(index).setFinish(globalStart, finish);;
}

public void DNFRecord()
{
	runs.peek().DNF();
	completedRuns.add(runs.poll());
}

public String toString() 
{
	StringBuilder concat = new StringBuilder("RUN\tBIB\tTIME\n");
	for(TimingRecord i : completedRuns)
	{
		concat.append(_runNumber + "\t" + i.toString() + "\n");
	}
	return concat.toString();
}

@Override
public void finishRecord(LocalDateTime finish) {
	return;
}

}