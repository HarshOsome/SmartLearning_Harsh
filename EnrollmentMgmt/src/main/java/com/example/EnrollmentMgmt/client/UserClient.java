
package com.example.EnrollmentMgmt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Replace url with your actual UserMgmt base URL/port.
// Later you can use service discovery instead of hardcoding URLs.
@FeignClient(name = "user-service", url = "http://localhost:4444", path = "/api/users")
public interface UserClient {

    @GetMapping("/{id}")
    UserDto getUser(@PathVariable("id") Long id);

    // You can add more end points if needed (e.g., minimal summary endpoint)
}
