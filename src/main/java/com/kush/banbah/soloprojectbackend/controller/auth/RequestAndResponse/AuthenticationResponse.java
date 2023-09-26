package com.kush.banbah.soloprojectbackend.controller.auth.RequestAndResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Class representing Authentication response
 */
@Data
@Builder
@AllArgsConstructor
public class AuthenticationResponse {

    /**
     * Token for future requests for the front-end
     */
    private String token;
}
