package com.lxp.course.course.application.port.in;

import com.lxp.course.course.application.port.in.command.DeleteChaptersCommand;

public interface DeleteChapterUseCase {
    void deleteChapterExecute(DeleteChaptersCommand command);
}
