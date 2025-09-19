package com.icici.demo.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import com.icici.demo.entities.Accounts;
import com.icici.demo.entities.Users;
import com.icici.demo.repos.AccountsRepository;
import com.icici.demo.dto.AccountInfoDTO;
import com.icici.demo.repos.UserRepository;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AccountsService {
    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    UserRepository userRepository;

    public List<AccountInfoDTO> getAllAccounts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        // List<AccountInfoDTO> accountDTOs = null;
        // Fetch paginated users
        List<AccountInfoDTO> accounts = userRepository.findAll(pageable).stream().flatMap(user -> {
            List<Accounts> userAccounts = user.getAccounts().stream().collect(Collectors.toList());
            return userAccounts.stream().map(account -> {
                AccountInfoDTO dto = new AccountInfoDTO();
                dto.setAccountNumber(account.getAccountNumber());
                dto.setAccountType(account.getAccountType());
                dto.setBalance(account.getBalance());
                dto.setAccountHolderName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setPhoneNumber(user.getPhoneNumber());
                return dto;
            });
        }).collect(Collectors.toList());
        System.out.println(accounts);
        return accounts;
        
    }
}