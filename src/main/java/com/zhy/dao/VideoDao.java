package com.zhy.dao;

import com.zhy.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoDao extends JpaRepository<Video, Long> {
}
