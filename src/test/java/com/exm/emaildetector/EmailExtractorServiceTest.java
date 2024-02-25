package com.exm.emaildetector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class EmailExtractorServiceTest {
    EmailExtractorService emailExtractorService = new EmailExtractorService();

    @Test
    @DisplayName("Test with null input")
    void shouldReturnEmptyListIfTextIsNull() {
        List<String> result = emailExtractorService.extractEmailsFromText(null);
        Assertions.assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("Test with empty input")
    void shouldReturnEmptyListIfTextIsEmptyString() {
        List<String> result = emailExtractorService.extractEmailsFromText("");
        Assertions.assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("Test with no email in text")
    void shouldReturnEmptyListIfTextHasNoEmail() {
        var text= """
                Morbi ultricies orci semper lacinia iaculis. Sed vel tortor mauris. Quisque in ante diam. Integer sagittis ex eros, non vehicula dui
                iaculis et. Morbi a nisi arcu. Nunc sagittis risus laoreet congue auctor. Aenean egestas dictum finibus. Aliquam commodo, quam non
                hendrerit accumsan, est nisi consequat orci, et pharetra nibh ipsum eu risus. Nullam aliquet pellentesque massa. Curabitur et turpis
                eu libero varius dapibus. Phasellus non placerat odio. Nulla nec est felis. Phasellus id molestie odio, nec pellentesque erat.""";

        List<String> result = emailExtractorService.extractEmailsFromText(text);
        Assertions.assertEquals(List.of(), result);
    }

    @Test
    @DisplayName("Test single email in text")
    void shouldDetectSingleEmailAddress() {
        var text = "email1@gmail.com";

        List<String> result = emailExtractorService.extractEmailsFromText(text);
        Assertions.assertEquals(List.of(text), result);
    }

    @Test
    @DisplayName("Test single email with long TLD in text")
    void shouldDetectSingleEmailAddressWithLongTLD() {
        var text = "email1@corporateX.co.uk";

        List<String> result = emailExtractorService.extractEmailsFromText(text);
        Assertions.assertEquals(List.of(text), result);
    }

    @Test
    @DisplayName("Test with special chars in front or after email address")
    void shouldDetectSingleEmailAddressWithHeadOrTailChars() {
        var email = "email1@gmail.com";
        var text = "." + email + "$";

        List<String> result = emailExtractorService.extractEmailsFromText(text);
        Assertions.assertEquals(List.of(email), result);
    }

    @Test
    @DisplayName("Test with multiple addresses in text")
    void shouldDetectMultipleAddresses() {
        var text = "Word1 word2  email1@gmail.com. Word3 word4  email2@gmail.com. Word5 word6";

        List<String> result = emailExtractorService.extractEmailsFromText(text);
        Assertions.assertEquals(List.of("email1@gmail.com", "email2@gmail.com"), result);
    }

    @Test
    @DisplayName("Test with single address in long text")
    void shouldDetectSingleEmailAddressFromLongText() {
        var email = "Email123@gmail.com";
        var text = """
                Integer eu purus hendrerit, hendrerit massa sed, tempor tortor. Quisque at sollicitudin eros. Donec ornare massa quis justo malesuada, id tempor magna tempor.
                Nullam lobortis sit amet libero ut elementum. Integer enim ipsum, maximus et ipsum vel, pulvinar scelerisque dui. Nunc rhoncus odio ac tellus tincidunt, vel
                efficitur velit dictum. Proin sagittis leo et finibus finibus. Maecenas nisi risus, sagittis ac arcu sed, elementum lacinia magna. Curabitur euismod, urna non
                tempus dapibus, quam lacus commodo lorem, ac interdum nisl orci pharetra metus. Duis sit amet orci in massa porttitor aliquet eget non neque. Etiam hendrerit
                nunc felis, auctor maximus erat iaculis vitae. Praesent nec tortor vitae ipsum suscipit blandit eget non odio. Duis mattis tristique semper. Phasellus vitae cursus urna.
                                
                Praesent in eros quis magna tristique viverra. Pellentesque eget faucibus turpis. Proin a libero ac risus laoreet bibendum id et dui. Phasellus semper, nibh
                elementum blandit mollis, massa nisl sollicitudin metus, eget eleifend felis ex et ante. Vestibulum varius a enim at scelerisque. Etiam elementum odio a viverra
                vestibulum. Donec id est congue, malesuada libero vel, condimentum erat"""
                + " " + email +
                """
                . Aliquam faucibus nisl purus, mollis egestas lorem porttitor at. Ut blandit nunc nec dapibus hendrerit. Sed non porta est. Duis id hendrerit lorem. Vestibulum
                pulvinar odio nec ex dapibus, a sodales sem dignissim. Donec mi lectus, hendrerit eu nunc a, eleifend venenatis tellus. Nam non neque id tellus dictum scelerisque.
                                
                Morbi ut neque massa. Donec tincidunt tincidunt felis, non venenatis dolor. Ut ac magna lorem. In nunc ante, laoreet vulputate nulla in, volutpat fringilla
                erat. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Quisque in felis tincidunt, ultrices lectus quis,
                venenatis sapien. Suspendisse tincidunt leo nec iaculis euismod. Suspendisse potenti. Suspendisse tellus erat, vestibulum aliquam pharetra vel, posuere
                ac tortor. Aliquam id mauris et odio condimentum rhoncus id ut quam. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec sit amet ex turpis.
                Fusce porta mauris eu enim posuere, sit amet tincidunt odio tempor. Etiam eu risus sed dui accumsan fringilla imperdiet porta leo. Aliquam eget iaculis nisl.
                Fusce gravida nibh id quam placerat finibus.
                                
                Cras lacinia vestibulum dui, faucibus sagittis ante dictum vitae. Maecenas neque ligula, bibendum eu dignissim quis, facilisis in dui. Sed porttitor velit sed
                quam gravida, a varius felis ornare. Nulla aliquam, lectus vitae sagittis mollis, justo est tempus velit, in pellentesque lorem nunc non sapien. Mauris vel
                feugiat dui, sed viverra sem. Sed in ullamcorper neque. Pellentesque dignissim nisl vitae posuere mattis. Vivamus faucibus velit elit, tristique lobortis augue
                interdum scelerisque. Quisque erat enim, aliquam ac est sed, tempus ultrices sapien. Ut suscipit dapibus tempor.
                                
                Integer diam risus, finibus et pharetra eu, maximus volutpat quam. In a interdum tellus, ac volutpat odio. Donec lacinia, mauris at hendrerit commodo, lorem
                augue sodales mi, non condimentum elit odio ultrices elit. Mauris gravida hendrerit lacus, ac euismod sem placerat ac. Donec malesuada tincidunt arcu quis
                ornare. Donec vel auctor mauris. Sed dignissim dui sed massa vehicula vestibulum. Nulla quis risus non elit scelerisque eleifend nec quis magna. Proin nec
                eleifend dolor. Sed ut interdum mauris. Vivamus id scelerisque lorem. Nulla eget congue tortor, id porttitor ex. Vestibulum at odio in augue maximus mollis.
                                
                Aliquam elementum eros id dapibus euismod. Duis eleifend orci eget sapien ultricies convallis. Fusce porttitor orci non elit auctor, nec consectetur nisi euismod.
                Suspendisse elit felis, congue faucibus felis sed, ullamcorper convallis massa. Cras eget congue odio, at venenatis lorem. Phasellus quis consequat massa. Cras
                rutrum nec metus eu volutpat. Nam sed massa maximus est mattis sagittis. Suspendisse et rhoncus turpis.
                                
                Morbi quam lectus, viverra in ante vitae, fermentum varius nisl. Nunc erat risus, tincidunt ac luctus quis, fermentum ac arcu. Maecenas lobortis porttitor
                pulvinar. Curabitur faucibus ipsum erat, vitae efficitur neque dignissim at. Cras sed ligula faucibus, ullamcorper tellus id, interdum lacus. Integer ut nisl
                nunc. Cras ornare convallis ligula, vitae efficitur turpis.
                                
                Donec aliquam ex molestie faucibus ullamcorper. Vivamus accumsan ut ipsum ut gravida. Maecenas iaculis ultrices lorem, in commodo arcu fermentum non. Fusce
                consectetur ligula at risus ornare dapibus. Aliquam tincidunt, erat id tincidunt semper, purus mi congue felis, eget pellentesque quam ipsum et tortor. Donec
                et nibh orci. Aenean non accumsan nibh. In in orci congue, elementum nunc in, interdum risus.
                                
                Mauris metus lorem, mattis quis risus eget, accumsan consequat mi. Quisque et tristique felis. Donec massa ligula, consequat ac metus a, lacinia ullamcorper
                ante. Sed id ligula erat. Sed eget iaculis dui. Etiam luctus, massa a viverra placerat, mi nibh finibus elit, id laoreet nunc massa non tellus. Curabitur
                elementum arcu eget dictum suscipit. Curabitur quis tempor leo. Nullam vitae imperdiet felis. Quisque mi tortor, pulvinar nec semper eu, pharetra dictum mi.
                
                """;

        List<String> result = emailExtractorService.extractEmailsFromText(text);
        Assertions.assertEquals(List.of(email), result);
    }
}
