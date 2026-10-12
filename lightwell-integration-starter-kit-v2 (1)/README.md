# Red Hat Lightwell Integration Starter Kit & Demo Repository

Welcome to the **Red Hat Lightwell Network & Clearinghouse Integration Starter Kit**. This repository is an enterprise-grade reference architecture, configuration template, and runnable demonstration designed for Platform Engineers, Security Architects (AppSec), and Development Leads.

Lightwell bridges the **"Vulnerability Gap"** in production applications by delivering surgical, drop-in security patches backported to the **exact pinned library versions** your applications already use (`x.y.z.rhlw-####`).

---

## 📂 Repository Structure

```text
.
├── README.md                               # Comprehensive architectural & onboarding guide
├── build-tools/                            # Client-side build tool configurations
│   ├── java/
│   │   ├── maven-settings.xml              # Maven ~/.m2/settings.xml template with auth
│   │   └── build.gradle.kts                # Gradle Kotlin DSL repository configuration
│   └── python/
│       ├── pip.conf                        # pip index & trusted host configuration
│       └── pyproject.toml                  # Poetry source configuration
├── repository-managers/                    # Enterprise proxy & mirroring configurations
│   ├── jfrog-artifactory/
│   │   ├── remote-maven-repo.json          # Artifactory Remote Maven Repo definition
│   │   └── remote-pypi-repo.json           # Artifactory Remote PyPI Repo definition
│   └── sonatype-nexus/
│       ├── nexus-maven-proxy.json          # Nexus Repository Manager 3 Maven Proxy
│       └── nexus-pypi-proxy.json           # Nexus Repository Manager 3 PyPI Proxy
├── demo-apps/                              # "Before & After" demonstration applications
│   ├── java-banking-app/                   # Java Spring Boot / Commons application
│   │   ├── pom.xml                         # Demonstrates pinned vulnerable vs .rhlw-#### fix
│   │   └── src/main/java/com/example/banking/WireTransferService.java
│   └── python-payment-service/             # Python Flask microservice
│       ├── requirements.txt                # Shows drop-in patched dependency resolution
│       └── app.py
├── automation/                             # Automated patching & agentic workflows
│   ├── openrewrite/
│   │   └── rewrite.yml                     # OpenRewrite recipe for automated .rhlw version bumping
│   └── ansible/
│       └── lightwell-fast-path-playbook.yml# Event-Driven Ansible (EDA) Fast Path orchestrator
├── ci-cd/                                  # Pipeline integration examples
│   ├── github-actions/
│   │   └── lightwell-pipeline.yml          # GitHub Actions workflow with SLSA & SBOM verification
│   └── gitlab-ci/
│       └── .gitlab-ci.yml                  # GitLab CI pipeline configuration
└── scripts/                                # Verification & operational utilities
    ├── query-lightwell-api.py              # CLI utility to query Lightwell Network API & CVE status
    ├── verify-java-provenance.sh           # Cosign / Sigstore SLSA L3 verification for JARs
    └── verify-python-provenance.sh         # Wheel extraction & SPDX SBOM validation script
```

---

## 🏛️ Architecture: The 3 Repository Tiers

Lightwell provides three distinct repository tiers in native repository formats:

1. **Validated (`validated`)**:
   * Upstream open-source libraries verified to contain no known or novel CVEs.
   * Drop-in replacement for Maven Central and PyPI using unmodified upstream version numbers.
2. **Remediated (`remediated`)**:
   * Contains surgical, backported security patches for pinned versions.
   * Format: `x.y.z.rhlw-####` (e.g., `commons-io:commons-io:2.11.0.rhlw-00001`).
   * **Zero breaking changes**: Retains 100% binary and API compatibility.
3. **Predisclosure (`predisclosure`)**:
   * Early-access patches delivering mitigations for novel zero-day flaws under embargo (minimum 14-day window before public disclosure).
   * New builds carry the `-nxxxx` version suffix.

---

## 🚀 Quickstart & Demo Walkthrough

### 1. Configure Build Authentication
Generate a **Service Account** in the [Red Hat Console](https://console.redhat.com/lightwell). 
Export your credentials:
```bash
export LIGHTWELL_USERNAME="rh-service-account-username"
export LIGHTWELL_PASSWORD="rh-service-account-token"
```

### 2. Maven Integration (Zero Code Changes)
Copy `build-tools/java/maven-settings.xml` to `~/.m2/settings.xml` or pass it directly:
```bash
cd demo-apps/java-banking-app
mvn clean package -s ../../build-tools/java/maven-settings.xml
```

### 3. Verify Supply Chain Provenance (SLSA Level 3)
Run the automated verification script on built artifacts:
```bash
./scripts/verify-java-provenance.sh \
  --jar demo-apps/java-banking-app/target/banking-service-1.0.0.jar \
  --group commons-io --artifact commons-io --version 2.11.0.rhlw-00001
```
This confirms:
* **Sigstore / Cosign** cryptographic signature.
* **SLSA Level 3** build provenance attestation (`.provenance.sigstore.json`).
* **CycloneDX SBOM** (`cyclonedx.json`) presence and integrity.

### 4. Query the Lightwell Network API
Check package availability and CVE remediation status programmatically:
```bash
python3 scripts/query-lightwell-api.py --cve CVE-2024-XXXX --ecosystem maven
```

---

## 👥 Persona Value Summary

* **Application Developers**: Keep certified dependency versions; no API breakage; sprint backlog reclaimed from alert triage.
* **Platform Engineers**: Single upstream proxy in Artifactory/Nexus; standard tooling; no agent sprawl.
* **AppSec & Compliance**: SLSA Level 3 provenance, signed SBOMs (CycloneDX/SPDX), and contractually backed embargoed patches.
