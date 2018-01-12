package action;

import java.util.ArrayList;

public class Satisfaction_Report extends Report{

	ArrayList<Survey> surveys;
	
	public Satisfaction_Report(String name, String quarter, String store, String year) 
	{
		super(name, quarter, store, year);
	}

	public ArrayList<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(ArrayList<Survey> surveys) {
		this.surveys = surveys;
	}
	
	public void calculateReport()
	{
		String report="";

		for(Survey s: surveys)
		{
			report += "\tSurvey ID: " + s.getID() +	"\n\tDate: " + s.getDate() + "\n\tNumber of participant: " + s.getNumOfParticipant();
			report += "\n\tAverage answers for quastions:";
			report += "\n\t"+ s.getQ1() + ": " + s.getA1();
			report += "\n\t"+ s.getQ2() + ": " + s.getA2();
			report += "\n\t"+ s.getQ3() + ": " + s.getA3();
			report += "\n\t"+ s.getQ4() + ": " + s.getA4();
			report += "\n\t"+ s.getQ5() + ": " + s.getA5();
			report += "\n\t"+ s.getQ6() + ": " + s.getA6();
			report += "\n\n\tExpert Conclusion: " + s.getConclusion() + "\n\n\n";
		}
		
		super.setDetails(report);
	}
	
	public String toString()
	{
		String report;
		report = super.getName() + "\nQuarter: " + super.getQuarter() + "\nDetails:\n\n" + super.getDetails();  
		return report;
	}

}
