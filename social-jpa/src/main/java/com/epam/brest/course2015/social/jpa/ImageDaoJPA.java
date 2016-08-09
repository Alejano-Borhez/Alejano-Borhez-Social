package com.epam.brest.course2015.social.jpa;

import com.epam.brest.course2015.social.core.Image;
import com.epam.brest.course2015.social.core.User;
import com.epam.brest.course2015.social.dao.ImageDao;
import com.epam.brest.course2015.social.test.Logged;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by alexander_borohov on 8.7.16.
 */
@Repository
public class ImageDaoJPA implements ImageDao {
    @Value("${image.selectAllImagesOfAUser}")
    private String selectImages;
    @Value("${image.deleteAllImagesOfAUser}")
    private String deleteImages;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Logged
    public Integer addImage(Image image) {
        entityManager.persist(image);
        Image addedImage = entityManager.merge(image);
        return addedImage.getImageId();
    }

    @Override
    @Logged
    public void deleteImage(Integer id) {
        try {
            entityManager.remove(getImage(id));

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new EmptyResultDataAccessException(id);
        }
    }

    @Override
    @Logged
    public void renameImage(Integer id, String filename) {
        Image newImage = entityManager.find(Image.class, id);
        newImage.setUrl(filename);
        entityManager.merge(newImage);
    }

    @Override
    @Logged
    public void deleteAllImages(Integer id) {
        entityManager.createQuery(deleteImages).setParameter("userId", id);
    }

    @Override
    @Logged
    public Image getImage(Integer imageId) {
        return entityManager.find(Image.class, imageId);
    }

    @Override
    @Logged
    @Cacheable("imagesList")
    public List<Image> getAllImagesOfAUser(Integer userId) {
        return entityManager.find(User.class, userId).getImages();
    }
}
