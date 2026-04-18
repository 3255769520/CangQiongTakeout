package com.hhxy.service;

import com.hhxy.vo.BusinessDataVO;
import java.time.LocalDateTime;

public interface WorkspaceService {
    BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end);
}