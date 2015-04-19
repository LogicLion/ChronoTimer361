import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GroupStream implements IStream {

private int _runNumber;
private int _bibNumber;
private LocalDateTime groupStart;
private Queue<TimingRecord> runs;
private Queue<TimingRecord> pendingRuns;
private ArrayList<TimingRecord> completedRuns;

public GroupStream(int runNumber)	
{
	_runNumber = runNumber;
	_bibNumber = 0;
	runs = new LinkedList<TimingRecord>();
	pendingRuns = new LinkedList<TimingRecord>();
	completedRuns = new ArrayList<TimingRecord>();
}

public void num(int BIB)
{
	return;
}

public void cancelRecord()
{
	_bibNumber++;
	completedRuns.add(new TimingRecord(_bibNumber, groupStart, TimingRecord.STATUS.CANCEL));
}

public void startRecord(LocalDateTime start)
{
	groupStart = start;
}

public void finishRecord(LocalDateTime finish)
{
	_bibNumber++;
	completedRuns.add(new TimingRecord(_bibNumber, groupStart, finish, TimingRecord.STATUS.FINISH));
}

public void DNFRecord()
{
	_bibNumber++;
	completedRuns.add(new TimingRecord(_bibNumber, groupStart, TimingRecord.STATUS.DNF));
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