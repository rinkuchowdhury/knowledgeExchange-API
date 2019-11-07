package community.innovation.ecosystem.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Component
@Document(collection = "token")
public class Token {
    private static int EXPIRATION=60*24;

    @Id
    private String tokenId;
    private String verificationToken;
    private User user;
    private Date createDate;
    private Date expiryDate;

    public Token(String verificationToken) {
        super();
        this.verificationToken = verificationToken;
        this.expiryDate=calculateExpiryDate(EXPIRATION);
    }

    public Token(String verificationToken, User user) {
        super();
        Calendar calendar=Calendar.getInstance();
        this.verificationToken = verificationToken;
        this.user = user;
        this.createDate=new Date(calendar.getTime().getTime());
        this.expiryDate=calculateExpiryDate(EXPIRATION);
    }
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE,expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }
}
