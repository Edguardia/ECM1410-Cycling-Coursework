package cycling;

public class Team {
    
    private int teamId;
    private String teamName;
    private String teamDescription;
    private Rider[] riders;

    public int getTeamID(){
        return teamId;
    }
    public String getTeamName(){
        return teamName;
    }
    public String getTeamDescription(){
        return teamDescription;
    }
    public Rider[] getRiders(){
        return riders;
    }
    public void setTeamID(int teamId){
        this.teamId = teamId;
    }
    public void setTeamName(String teamName){
        this.teamName = teamName;
    }
    public void setTeamDescription(String teamDescription){
        this.teamDescription = teamDescription;
    }
    public void setRiders(Rider[] riders){
        this.riders = riders;
    }
}
