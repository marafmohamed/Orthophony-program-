package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.CatAnamAdulte;
import esi.tp.tp_poo.Enums.CatAnamEnfant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Anamnese {
    private int Anamnese_id;
    private List<QuestAnam> questions;
    private int Bilan_id;
    public Anamnese(int anamneseId,int Bilan_id) {
        if(anamneseId>0){
            Anamnese a= getAnamnese(anamneseId);
            this.Anamnese_id = anamneseId;
            this.questions = a.getQuestions();
            this.Bilan_id=Bilan_id;
        }else {
            insertAnamnese();
            this.questions = new ArrayList<>();
            this.Bilan_id=Bilan_id;
        }

    }

    public void addQuestionEnfant(String questionText, String answerText, CatAnamEnfant category, int Q_id) {
        QuestAnamEnfant Qestion = new QuestAnamEnfant(questionText, answerText, category, this.Anamnese_id, Q_id);
        questions.add(Qestion);
    }

    public void addQuestionAdulte(String questionText, String answerText, CatAnamAdulte category, int Q_id) {
        QuestAnamAdulte Qestion = new QuestAnamAdulte(questionText, answerText, category, this.Anamnese_id, Q_id);
        questions.add(Qestion);
    }

    public List<QuestAnam> getQuestions() {
        return questions;
    }

    public void addQuestion(QuestAnam question) {
        this.questions.add(question);
    }
    public void insertAnamnese(){
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "INSERT INTO Anamnese (Bilan) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, Bilan_id);
            pstmt.executeUpdate();

            // Retrieve the generated keys (in this case, just one)
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                this.Anamnese_id = generatedKeys.getInt(1); // Assuming the ID is in the first column
            } else {
                throw new SQLException("Creating TestQuestions failed, no ID obtained.");
            }

            System.out.println("Anamnese added to the database successfully. Test ID: " + this.Anamnese_id);
        } catch (SQLException e) {
            System.out.println("Error inserting TestQuestions: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Contract(value = "_ -> new", pure = true)
    public static @NotNull Anamnese getAnamnese(int anamnese_id) {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();

        String sql = "SELECT * FROM Anamnese WHERE Anamnese_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, anamnese_id);
            ResultSet rs = pstmt.executeQuery();
            Anamnese anamnese = null;
            while (rs.next()) {
                anamnese  = new Anamnese(anamnese_id,rs.getInt("Bilan"));
                String sql2 = "SELECT * FROM QuestAnam WHERE Anamnese = ?";
                try (PreparedStatement pstmt2 = connection.prepareStatement(sql2)) {
                    pstmt2.setInt(1, rs.getInt("Anamnese_id"));
                    ResultSet rs2 = pstmt2.executeQuery();

                    while (rs2.next()) {
                        Boolean Enfant = rs2.getBoolean("Enfant");
                        if (Enfant) {
                            anamnese.addQuestionEnfant(rs2.getString("Enonce"), rs2.getString("Reponse"), CatAnamEnfant.valueOf(rs2.getString("Category")), rs2.getInt(1));
                        } else {
                            anamnese.addQuestionAdulte(rs2.getString("Enonce"), rs2.getString("Reponse"), CatAnamAdulte.valueOf(rs2.getString("Category")), rs2.getInt(1));
                        }
                    }
                }
            }
            return anamnese;
        } catch (SQLException e) {
            System.out.println("Error fetching.");
            e.printStackTrace();
        }
        return null;
    }
}
