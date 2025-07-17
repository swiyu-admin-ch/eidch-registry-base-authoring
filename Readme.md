<!--
SPDX-FileCopyrightText: 2025 Swiss Confederation

SPDX-License-Identifier: MIT
-->

![github-banner](https://github.com/swiyu-admin-ch/swiyu-admin-ch.github.io/blob/main/assets/images/github-banner.jpg)

# Datastore Services and Libraries

The datastore services are the backbone of the swiss SSI inspired ecosystem.
This repository does contain the current implementation for the base registry. The authoring service is a service with
write-rights.

## Table of Contents

- [Datastore Services and Libraries](#datastore-services-and-libraries)
  - [Table of Contents](#table-of-contents)
  - [Overview](#overview)
  - [Installation](#installation)
  - [Usage](#usage)
    - [Auth](#auth)
      - [OAuth configuration](#oauth-configuration)
  - [Missing Features and Known Issues](#missing-features-and-known-issues)
  - [Contributions and feedback](#contributions-and-feedback)
  - [License](#license)

## Overview

For a general overview of the public beta environment and its components, please check
the [Public Beta context](https://swiyu-admin-ch.github.io/open-source-components/#public-beta).

A datastore service always includes 2 sub services:

1. The authoring service, which essentially provides all the write operations.  
   Those should only be available to specified authorized systems like the controller system provided by the swiss gov.
2. The data service, which provides all the protocol conform read operations.
   Therefore we do require a strict separation of write operations in the code, so while the data models are shared
   through
   the shared libraries services and repositories are not shared.

## Installation

> [!NOTE]
> Starting the service with the local profile does not connect the data and authoring services. It only serves to start
> this service locally.

To install docker please follow the instructions on the respective pages.

In order to start the service locally, run:

```shell
docker compose up
```

Then run:

```shell
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

The openapi definition can then be found [here](http://localhost:8180/swagger-ui.html)

## Usage

### Auth

The data service is always unprotected as all data on the registers should be readable by everyone.  
The authoring services do need protection, which is as of now handled by OAuth2.0.  
We do recommend to add mTLS authentication to your infrastructure endpoints.

#### OAuth configuration

This service supports OAuth Multi-Tenancy. This means more than one OAuth authorization server can be used.
The authorization servers must be OpenID Connect compliant.

Trusted authorization services can be added under the property `spring.security.oauth2.trusted-oauth-issuers.*`

This can also be done using spring environment variables.
`SECURITY_OAUTH2_JWT_ISSUERURIS_*`

_Example_

```
SECURITY_OAUTH2_JWT_ISSUERURIS_MYAUTHSERVER="https://www.example.com/auth"
SECURITY_OAUTH2_JWT_ISSUERURIS_OTHERAUTHSERVER="https://www.other.example.com/auth"
```

For being compliant the authorization server must create Bearer JWTs with the provided URI as issuer in the "iss" field.
Furthermore, the OpenID Configuration must be available under
`https://www.example.com/auth/.well-known/openid-configuration`

#### Monitoring configuration

Currently, it is only possible to use the basic auth capabilities on the `/actuator/prometheus` endpoint.

To configure the basic auth capabilities please use the following environment variables:

| Name                           | Type    | Description                                                 |
| ------------------------------ | ------- | ----------------------------------------------------------- |
| MONITORING_BASIC_AUTH_USERNAME | String  | Defines the password used during the basic auth validation. |
| MONITORING_BASIC_AUTH_PASSWORD | String  | Defines the password used during the basic auth validation. |

## Missing Features and Known Issues

The swiyu Public Beta Trust Infrastructure was deliberately released at an early stage to enable future ecosystem participants. The [feature roadmap](https://github.com/orgs/swiyu-admin-ch/projects/1/views/7) shows the current discrepancies between Public Beta and the targeted productive Trust Infrastructure. There may still be minor bugs or security vulnerabilities in the test system. These are marked as [‘KnownIssues’](../../issues) in each repository.

## Contributions and feedback

The code for this repository is developed privately and will be released after each sprint. The published code can
therefore only be a snapshot of the current development and not a thoroughly tested version. However, we welcome any
feedback on the code regarding both the implementation and security aspects. Please follow the guidelines for
contributing found in [CONTRIBUTING.md](/CONTRIBUTING.md).

## License

This project is licensed under the terms of the MIT license. See the [LICENSE](/LICENSE) file for details.
