package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestQuestions extends Test {

    private ArrayList<Question> Questionnaire;
    private int scoreTotal;
    final boolean exo = false;

    public TestQuestions(String nomTest, String capacité, int patient, int compteRendu,int Bilan, int Test_id) {
        super(nomTest, capacité, patient, compteRendu, Bilan);
        this.Questionnaire = new ArrayList<Question>();
        if(Test_id>0){
            this.Test_id=Test_id;
            try {
                retrieveQuestionsFromDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else {
            insertTest();
        }
    }

    public int CalculerScore() {
        for (Question question : Questionnaire) {
            scoreTotal += question.score;
        }
        return scoreTotal;
    }

    public void addQuestionLibre(String enonce, List<String> propositions, int score) {
        Question question = new Question(enonce, propositions, score, this.Test_id, 0);
        Questionnaire.add(question);
    }

    public void addQCM(String enonce, List<String> propositions, List<Integer> reponsesCorrectes, int score) {
        Question question = new QCM(enonce, propositions, reponsesCorrectes, this.Test_id, 0);
        Questionnaire.add(question);
    }

    public void addQCU(String enonce, ArrayList<String> propositions, int reponse, int score) {
        Question question = new QCU(enonce, propositions, reponse, this.Test_id, 0);
        Questionnaire.add(question);
    }

    @Override
    public void insertTest() {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO TestQuestions (nom, Capacite, Patient,exo) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nomTest);
            pstmt.setString(2, this.Capacité);
            pstmt.setInt(3, this.patient);
            pstmt.setBoolean(4,this.exo);
            pstmt.executeUpdate();

            // Retrieve the generated keys (in this case, just one)
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.Test_id = generatedKeys.getInt(1); // Assuming the ID is in the first column
            } else {
                throw new SQLException("Creating TestQuestions failed, no ID obtained.");
            }

            System.out.println("TestExercice added to the database successfully. Test ID: " + this.Test_id);
        } catch (SQLException e) {
            System.out.println("Error inserting TestQuestions: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void retrieveQuestionsFromDB() throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "SELECT * FROM Question WHERE Test_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, this.Test_id);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int questionId = rs.getInt("Question_id");
                    String enonce = rs.getString("Enonce");
                    int score = rs.getInt("score");

                    // Retrieve propositions (assuming propositions are stored in a separate table or as a delimited string)
                    String propositions = rs.getString(3);
                    String reponse= rs.getString(5);
                    if (reponse ==null) {
                        Question question = new Question(enonce,new ArrayList<String>(), score, this.Test_id, questionId);
                        this.Questionnaire.add(question);
                    }else if(reponse.contains("[")){
                        List<Integer>reponsesCorrectes=convertStringToIntegerList(reponse);
                        List<String> props = convertStringToStringList(propositions);
                        Question question = new QCM(enonce,props, reponsesCorrectes, this.Test_id, questionId);
                        this.Questionnaire.add(question);
                    } else {
                        int reponseC = Integer.parseInt(reponse);
                        List<String> props = convertStringToStringList(propositions);
                        Question question = new QCU(enonce, (ArrayList<String>) props, reponseC, this.Test_id, questionId);
                        this.Questionnaire.add(question);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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
