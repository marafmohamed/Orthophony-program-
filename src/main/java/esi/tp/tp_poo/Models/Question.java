package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Question {
    protected int Question_id;
    protected String enonce;
    protected List<String> propositions;
    protected int score;
    public Question(String enonce, List<String> propositions, int score, int Test_id, int Question_id) {
        this.enonce = enonce;
        this.propositions = propositions;
        this.score = 0;
        if(Question_id > 0){
            this.Question_id = Question_id;
        }else {
            try {
                insertQuestion();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public int getQuestion_id() {
        return Question_id;
    }

    public String getEnonce() {
        return enonce;
    }

    public List<String> getPropositions() {
        return propositions;
    }

    public int getScore() {
        return score;
    }

    // Setters
    public void setEnonce(String enonce) throws SQLException {
        this.enonce = enonce;
        updateAttributeInDatabase("enonce", enonce);
    }

    public void setPropositions(List<String> propositions) {
        this.propositions = propositions;
        // Assuming Propositions are not updated in database
    }

    public void setScore(int score) throws SQLException {
        this.score = score;
        updateAttributeInDatabase("score", String.valueOf(score));
    }

//    public void setReponse(String reponse) throws SQLException {
//        this.reponse = reponse;
//        updateAttributeInDatabase("reponse", reponse);
//    }

    private void updateAttributeInDatabase(String columnName, String value) throws SQLException {
        if (this.Question_id <= 0) {
            throw new IllegalArgumentException("Question_id is not valid. Unable to update database.");
        }

        try (Connection connection = ConnectDB.getInstance().getConnection();
             PreparedStatement pstmt = connection.prepareStatement("UPDATE Question SET " + columnName + " = ? WHERE Question_id = ?")) {

            pstmt.setString(1, value);
            pstmt.setInt(2, this.Question_id);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("No rows were updated for Question_id " + this.Question_id);
            }

            System.out.println(columnName + " updated in the database successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating " + columnName + " in database: " + e.getMessage());
            throw e;
        }
    }
    public void insertQuestion() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        // Assuming the table name is "Question" and it has columns for enonce, propositions, score, and reponse
        String sql = "INSERT INTO Question (Enonce, Propositions, score) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.enonce);
            pstmt.setString(2, String.join(",", this.propositions)); // Join propositions into a single string
            pstmt.setInt(3, this.score);
            pstmt.executeUpdate();

            // Retrieve the generated keys (if any)
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    this.Question_id = generatedKeys.getInt(1); // Assuming the ID is in the first column
                } else {
                    throw new SQLException("Creating Question failed, no ID obtained.");
                }
            }

            System.out.println("Question added to the database successfully. Question ID: " + this.Question_id);
        } catch (SQLException e) {
            System.out.println("Error inserting Question: " + e.getMessage());
            throw e;
        }
    }
    public static Question getQuestionById(int question_id) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT * FROM Question WHERE Question_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, question_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int questionId = rs.getInt("Question_id");
                    String enonce = rs.getString("Enonce");
                    int score = rs.getInt("score");

                    // Retrieve propositions (assuming propositions are stored in a separate table or as a delimited string)
                    String propositions = rs.getString(3);
                    String reponse= rs.getString(5);
                    if (reponse ==null) {
                       return new Question(enonce,new ArrayList<String>(), score, 0, questionId);
                    }else if(reponse.contains("[")){
                        List<Integer>reponsesCorrectes=convertStringToIntegerList(reponse);
                        List<String> props = convertStringToStringList(propositions);
                       return new QCM(enonce,props, reponsesCorrectes, 0, questionId);
                    } else {
                        int reponseC = Integer.parseInt(reponse);
                        List<String> props = convertStringToStringList(propositions);
                        return  new QCU(enonce, (ArrayList<String>) props, reponseC, 0, questionId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public static List<Integer> convertStringToIntegerList(String str) {
        // Remove the square brackets
        str = str.substring(1, str.length() - 1);

        // Split the string by commas
        String[] items = str.split(",");

        // Convert string elements to integers and collect them into a list
        List<Integer> resultList = new ArrayList<>();
        for (String item : items) {
            resultList.add(Integer.parseInt(item.trim()));
        }

        return resultList;
    }
    public static List<String> convertStringToStringList(String str) {
        // Remove the square brackets
        str = str.substring(1, str.length() - 1);

        // Split the string by commas while taking care of quoted strings
        String[] items = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

        // Remove the quotes around each item and collect them into a list
        List<String> resultList = new ArrayList<>();
        for (String item : items) {
            resultList.add(item.trim().replaceAll("^\"|\"$", ""));
        }

        return resultList;
    }

}
