package comunicationcenter.service.exceptions;

import comunicationcenter.service.dto.base.ConcreteDTO;
import comunicationcenter.service.dto.base.AbstractDTO;
import comunicationcenter.service.dto.base.MessageDTO;

import java.util.List;

/**
 * Created by simon.calabrese on 31/10/2017.
 */
public class BaseException extends Exception {
    private static final long serialVersionUID = 149950736573768028L;

    private AbstractDTO exceptionDto;

    public BaseException(AbstractDTO exceptionDto) {
        this.exceptionDto = exceptionDto;
    }

    public BaseException(List<MessageDTO> messages) {
        final AbstractDTO dto = new ConcreteDTO();
        dto.setMessages(messages);
    }

    public AbstractDTO getExceptionDto() {
        return exceptionDto;
    }
}
