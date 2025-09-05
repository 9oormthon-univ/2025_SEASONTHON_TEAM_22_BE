package goormthonuniv.team_22_be.notification.support;

import goormthonuniv.team_22_be.notification.domain.model.PushSubscription;
import lombok.RequiredArgsConstructor;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Security;

@Component
@RequiredArgsConstructor
public class WebPushSender {

    @Value("${spring.vapid.public-key}")
    private String publicKey;

    @Value("${spring.vapid.private-key}")
    private String privateKey;

    @Value("${spring.vapid.subject}")
    private String subject;

    public void send(PushSubscription sub, String title, String body) throws Exception {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        var keys = new Subscription.Keys(sub.getP256dh(), sub.getAuth());
        var subscription = new Subscription(sub.getEndpoint(), keys);
        var notification = new Notification(subscription,

                """
                { "title": "%s", "body": "%s" }
                """.formatted(escape(title), escape(body))
        );

        var push = new PushService(publicKey, privateKey, subject);
        push.send(notification);
    }

    private String escape(String s) {
        return s == null ? "" : s.replace("\"", "\\\"");
    }
}