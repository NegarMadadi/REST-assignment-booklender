package se.lexicon.negar.booklender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.negar.booklender.data.LibraryUserRepository;
import se.lexicon.negar.booklender.dto.LibraryUserDto;
import se.lexicon.negar.booklender.entity.LibraryUser;

import java.util.List;

@Service
@Configurable
public class LibraryUserServiceImpl implements LibraryUserService {

    LibraryUserRepository libraryUserRepository;

    @Autowired
    public LibraryUserServiceImpl(LibraryUserRepository libraryUserRepository) {
        this.libraryUserRepository = libraryUserRepository;
    }

    protected LibraryUser getLibraryUser(LibraryUserDto libraryUserDto){

        return new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
    }

    @Override
    public LibraryUserDto findById(int userId) throws RuntimeException {
        if (!libraryUserRepository.existsById(userId))
            throw new RuntimeException("Cannot find any library user with id: " + userId);
        LibraryUser libraryUser = libraryUserRepository.findByUserId(userId);
        return new LibraryUserDto(libraryUser);
    }

    @Override
    public LibraryUserDto findByEmail(String email) throws IllegalArgumentException{
        if (email == null)
            throw new IllegalArgumentException("Email can not be empty");
        if (libraryUserRepository.findByEmailIgnoreCase(email) == null)
            throw new IllegalArgumentException("User with " + email + " email doesn't exist");
        LibraryUser libraryUser = libraryUserRepository.findByEmailIgnoreCase(email);
        return new LibraryUserDto(libraryUser);
    }

    @Override
    public List<LibraryUserDto> findAll() {
        List<LibraryUser> foundItems = libraryUserRepository.findAll();
        return LibraryUserDto.toLibraryUserDtos(foundItems);
    }

    @Override
    @Transactional
    public LibraryUserDto create(LibraryUserDto libraryUserDto) throws RuntimeException {
        if (libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user duplicated");
        LibraryUser toCreate = new LibraryUser(libraryUserDto.getRegDate(), libraryUserDto.getName(), libraryUserDto.getEmail());
        return new LibraryUserDto(libraryUserRepository.save(toCreate));
    }

    @Override
    @Transactional
    public LibraryUserDto update(LibraryUserDto libraryUserDto) throws RuntimeException {
        if (!libraryUserRepository.existsById(libraryUserDto.getUserId()))
            throw new RuntimeException("Library user does'nt' exist");
        LibraryUser user = libraryUserRepository.findById(libraryUserDto.getUserId()).orElseThrow(
                () -> new RuntimeException("User was not found.")
        );
        if (!user.getName().equals(libraryUserDto.getName()))
            user.setName(libraryUserDto.getName());
        if (!user.getEmail().equalsIgnoreCase(libraryUserDto.getEmail()))
            user.setEmail(libraryUserDto.getEmail());
        if (user.getRegDate() != libraryUserDto.getRegDate())
            user.setRegDate(libraryUserDto.getRegDate());
        return new LibraryUserDto(libraryUserRepository.save(user));
    }

    @Override
    @Transactional
    public boolean delete(int userId) throws IllegalArgumentException{
        if (!libraryUserRepository.findById(userId).isPresent())
            throw new IllegalArgumentException("User with this id doesn't exist");
        boolean deleted = false;
        if (libraryUserRepository.existsById(userId)){
            libraryUserRepository.delete(libraryUserRepository.findById(userId).get());
            deleted = true;
        }
        return deleted;
    }
}
