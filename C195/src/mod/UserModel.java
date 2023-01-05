package mod;

public class UserModel {

    // local variables
    private int userId;
    private String username;
    private String password;

    /**
     * @param userId Integer variable holding user's identification
     * @param username String object holding the user's name
     * @param password String object holding the user's password
     */
    public UserModel (int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    /**
     * userId getter
     * @return userId
     */
    public int getUserId() {return userId;}

    /**
     * userId setter
     * @param userId Integer variable to hold ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Username getter
     * @return username String object to hold username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Username setter
     * @param username String object to hold username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Pword setter for user's password
     * @param password String Object holding password
     */
    public void setPword(String password) {
        this.password = password;
    }
    /** Pword getter for user's password
     * @return password String object holding password
     */

    public String getPword() {
        return password;
    }

}
