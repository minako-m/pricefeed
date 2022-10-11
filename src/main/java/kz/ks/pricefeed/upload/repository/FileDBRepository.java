package kz.ks.pricefeed.upload.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kz.ks.pricefeed.upload.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}