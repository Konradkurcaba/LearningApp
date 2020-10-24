package pl.kurcaba.learn.helper.persistence.file;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.kurcaba.learn.helper.common.model.LearnCase;

import java.time.Instant;
import java.util.UUID;

@Disabled
class LearnCaseDtoTest
{
    private LearnCase createExampleLearnCase()
    {
        LearnCase learnCase = new LearnCase("name", "definition"
                , UUID.fromString("6681ce3f-607f-42ca-8f91-6294646dad92"),true, Instant.now());
        byte[] dummyImage = new byte[2];
        learnCase.setImage(dummyImage);
        return learnCase;
    }

    @Test
    public void shouldLearnCaseBeCopiedCorrectly()
    {
        LearnCase learnCase = createExampleLearnCase();
        LearnCaseDto learnCaseDto = new LearnCaseDto(learnCase);
        LearnCase copiedLearnCase = learnCaseDto.toLearnCase();

        Assertions.assertEquals(learnCase, copiedLearnCase);
    }

    @Test
    public void LearnCaseCopyShouldNotBeTheSameObject()
    {
        LearnCase learnCase = createExampleLearnCase();
        LearnCaseDto learnCaseDto = new LearnCaseDto(learnCase);
        LearnCase copiedLearnCase = learnCaseDto.toLearnCase();

        Assertions.assertNotSame(learnCase, copiedLearnCase);
    }

}