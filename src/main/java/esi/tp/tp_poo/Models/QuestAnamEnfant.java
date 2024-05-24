package esi.tp.tp_poo.Models;

import esi.tp.tp_poo.ConnectDB;
import esi.tp.tp_poo.Enums.CatAnamEnfant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuestAnamEnfant extends QuestAnam{
    private CatAnamEnfant category;

    public QuestAnamEnfant(String questionText, String answerText,CatAnamEnfant category,int anamnese,int QestionAnam_id) {
        super(questionText,answerText,anamnese);
        this.category = category;
        if(QestionAnam_id == -1){
            try {
                insertQuestAnam(anamnese);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException(e);
            }
        }else{
            this.Question_id=QestionAnam_id;
        }
    }

    public CatAnamEnfant getCategory() {
        return category;
    }

    public void setCategory(CatAnamEnfant category) {
        this.category = category;
    }

    public void insertQuestAnam(int anamnese) throws SQLException {
        ConnectDB db = ConnectDB.getInstance();
        Connection connection = db.getConnection();
        String sql = "INSERT INTO Question_Anamnese (Enonce,Category,Reponse,Anamnese,Enfant) VALUES (?, ?, ?, ?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, this.getQuestionText());
        pstmt.setString(2, this.category.toString());
        pstmt.setString(3, this.answerText);
        pstmt.setInt(4,anamnese);
        pstmt.setBoolean(5,true );
        pstmt.executeUpdate();
        pstmt.close();
    }
}
