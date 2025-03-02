# Configuration Manager - Singleton Pattern Implementation

## Setup Configuration

1. Navigate to the `src/config` directory
2. Copy the template configuration file:
   ```bash
   cp template.config.properties config.properties
   ```
3. Edit `config.properties` with your actual configuration values:
   ```properties
   database.url=your_database_url
   database.username=your_username
   database.password=your_password
   ```

## Security Notes
- Never commit `config.properties` to the repository
- Keep your sensitive information in `config.properties`
- Use `template.config.properties` as a reference for required configuration
- Consider using environment variables for highly sensitive data