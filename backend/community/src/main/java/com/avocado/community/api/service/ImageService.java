package com.avocado.community.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    public String createStyleshotImage(MultipartFile file) {
        String styleshotImageUrl = uploadImage(file, "styleshots");
        return styleshotImageUrl;
    }

    public String uploadImage(MultipartFile multipartFile, String dirName) {
        // 사진 파일이 아닐 경우 종료
        if (multipartFile.getContentType() == null || !multipartFile.getContentType().startsWith("image/")) {
            return null;
        }

        // S3 업로드 할 파일 이름 생성
        String s3FileName = dirName + "/" + UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            // MultipartFile -> File 변환
            File convertFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 변환 실패"));

            // S3 업로드 및 URL 획득
            String url = putS3(convertFile, s3FileName);

            // 로컬 파일 삭제
            removeTempFile(convertFile);

            return url;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(
                        bucket, fileName, uploadFile
                ).withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeTempFile(File targetFile) {
        if (!targetFile.delete()) {
            log.warn("파일이 삭제되지 않았습니다.");
        }
    }

    private Optional<File> convert(MultipartFile multipartFile) throws IOException {
        // 로컬 파일 생성 및 반환
        File convertFile = new File(UUID.randomUUID().toString());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(multipartFile.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }
}
