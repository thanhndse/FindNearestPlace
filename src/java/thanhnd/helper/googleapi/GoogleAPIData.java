/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thanhnd.helper.googleapi;

import java.util.List;

/**
 *
 * @author thanh
 */
public class GoogleAPIData {
    private List<Candidate> candidates;
    private String status;
    private String error_message;

    public GoogleAPIData() {
    }
    

    public GoogleAPIData(List<Candidate> candidates, String status) {
        this.candidates = candidates;
        this.status = status;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
    
    
}
