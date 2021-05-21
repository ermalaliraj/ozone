package al.ozone.engine.batch.jobs;

public interface BatchJobInterface {

    public void execute() throws Exception ;
    
    /**
     * @return Job name
     */
    public String getJobName();
    
}
