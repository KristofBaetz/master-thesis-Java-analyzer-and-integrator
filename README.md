# Topic and Sentiment Identifier

A comprehensive Java application for analyzing sentiment and topic identification in news articles and social media data related to the 2016 US Presidential Election campaign. The project combines natural language processing, RDF data processing, and database analytics to provide insights into political discourse.

## 🚀 Features

### Core Functionality
- **Sentiment Analysis**: Uses Stanford CoreNLP for analyzing sentiment in news articles and social media content
- **Topic Identification**: Identifies and tracks mentions of presidential candidates in articles
- **Social Media Processing**: Processes Twitter data from N3/RDF files
- **Poll Data Integration**: Imports and processes polling data from CSV files
- **Multi-threaded Processing**: Utilizes available CPU cores for efficient sentiment analysis
- **Database Integration**: Stores and retrieves data using MariaDB

### Data Sources
- News articles from various sources during the 2015-2016 election campaign period
- Twitter data in RDF/N3 format
- Polling data from CSV files (2015 polls, 2016 polls, post-convention polls)
- Candidate information and campaign data

## 🛠 Technology Stack

- **Java 17**: Core programming language
- **Stanford CoreNLP 4.5.6**: Natural language processing and sentiment analysis
- **Apache Jena**: RDF data processing and SPARQL querying
- **MariaDB**: Database for data storage
- **OpenCSV**: CSV file processing
- **Maven**: Build and dependency management

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MariaDB database server
- Minimum 8GB RAM (recommended for large-scale NLP processing)

## 🔧 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd TopicAndSentimentIdentifier
   ```

2. **Install dependencies**
   ```bash
   mvn clean install
   ```

3. **Database Setup**
   - Install and configure MariaDB
   - Update database connection details in `NewsArticleDatabase.java`
   - Create necessary database tables (schema not included in this repository)

4. **Data Files**
   - Place polling CSV files in the project root:
     - `After convention polls-UTF-8.csv`
     - `Polls Conducted in 2015-UTF-8.csv`
     - `Polls Conducted in 2016-UTF-8.csv`
   - Ensure Twitter RDF data files are available in the specified input directory

## 🚀 Usage

### Running Different Components

#### 1. Sentiment Analysis
```bash
# Single-threaded sentiment analysis
java -cp target/classes org.baetz.christoph.sentimentanalyzer.SentimentAnalyser

# Multi-threaded sentiment analysis (recommended)
java -cp target/classes org.baetz.christoph.sentimentanalyzer.sentimentAnalyser.SentimentAnalyserExecutor
```

#### 2. Candidate Mention Analysis
```bash
java -cp target/classes org.baetz.christoph.driver.CandidatesMentionedOverTime
```

#### 3. Poll Data Import
```bash
java -cp target/classes org.baetz.christoph.pollDataImporter.PollsDataImporter
```

#### 4. RDF Data Processing
```bash
java -cp target/classes org.baetz.christoph.databaseGraphLoader.Main
```

### Key Classes and Their Functions

- **`SentimentAnalyserExecutor`**: Multi-threaded sentiment analysis of articles
- **`CandidatesMentionedOverTime`**: Tracks candidate mentions over time and exports to CSV
- **`PollsDataImporter`**: Imports polling data from CSV files
- **`Main`** (databaseGraphLoader): Processes compressed RDF files containing Twitter data
- **`CandidateAppearanceAnalyser`**: Analyzes candidate appearances in articles
- **`HashTagExtractor`**: Extracts hashtags from article content

## 📊 Data Processing Pipeline

### 1. RDF Data Processing
```
Compressed N3 files → Decompression → Splitting → SPARQL Querying → Database Storage
```

### 2. Article Analysis
```
Database Articles → Content Processing → Sentiment Analysis → Results Storage
```

### 3. Candidate Tracking
```
Articles + Candidate List → Text Matching → Temporal Analysis → CSV Export
```

## 🗂 Project Structure

```
src/
├── main/java/org/baetz/christoph/
│   ├── analyser/              # Analysis components
│   ├── dao/                   # Data Access Objects
│   ├── database/              # Database connection utilities
│   ├── databaseGraphLoader/   # RDF data processing
│   ├── driver/                # Main execution classes
│   ├── models/                # Data models
│   ├── pollDataImporter/      # Poll data processing
│   └── sentimentanalyzer/     # Sentiment analysis components
└── test/java/                 # Unit tests
```

## 🎯 Key Features in Detail

### Sentiment Analysis
- Uses Stanford CoreNLP's neural network-based sentiment classifier
- Processes articles sentence by sentence
- Multi-threaded processing for improved performance
- Handles large datasets efficiently

### Candidate Tracking
The system tracks mentions of these 2016 presidential candidates:

**Democratic Candidates:**
- Hillary Clinton
- Bernie Sanders
- Martin O'Malley
- Lincoln Chafee
- Jim Webb

**Republican Candidates:**
- Donald Trump
- John Kasich
- Ted Cruz
- Marco Rubio
- Ben Carson
- Jeb Bush
- Chris Christie
- Carly Fiorina
- Rick Santorum
- Rand Paul
- Mike Huckabee
- George Pataki
- Lindsey Graham
- Bobby Jindal
- Scott Walker
- Rick Perry

### Data Export
- Generates CSV files with candidate mention counts over time
- Provides daily granularity for the campaign period (2015-01-01 to 2016-11-08)
- Results saved to `./results/candidatesMentionedOverTime.csv`

## ⚙️ Configuration

### Database Configuration
Update the connection details in `NewsArticleDatabase.java`:
```java
connConfig.setProperty("user", "your_username");
connConfig.setProperty("password", "your_password");
// Update connection URL with your database details
```

### Memory Configuration
For large datasets, consider increasing JVM memory:
```bash
java -Xmx8g -Xms4g -cp target/classes [MainClass]
```

## 📈 Performance Considerations

- **Multi-threading**: The sentiment analyzer automatically detects available CPU cores
- **Memory Usage**: Large NLP models require significant memory
- **Database Connections**: Ensure proper connection pooling for high-throughput operations
- **RDF Processing**: Uses in-memory datasets for faster processing

## 🧪 Testing

Run the test suite:
```bash
mvn test
```

Key test classes:
- `TestSentimentAnalyser`: Tests sentiment analysis functionality
- `TestArticleCount`: Validates article counting and database queries
- `TestCandidates`: Tests candidate data retrieval

## 📄 Output Files

The application generates several output files:
- `./results/candidatesMentionedOverTime.csv`: Time series data of candidate mentions
- Database records for processed articles, tweets, and polls
- Intermediate files during RDF processing (automatically cleaned up)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## ⚠️ Important Notes

- This project was designed for research into the 2016 US Presidential Election
- Requires significant computational resources for full-scale processing
- Database schema needs to be created separately
- Twitter data processing requires properly formatted N3/RDF files
- Ensure proper licensing compliance when using Stanford CoreNLP models

## 📚 Dependencies

See `pom.xml` for complete dependency list. Key dependencies include:
- Stanford CoreNLP with models
- Apache Jena for RDF processing
- MariaDB JDBC driver
- OpenCSV for file processing
- Logback for logging

## 📧 Support

For questions or issues, please create an issue in the GitHub repository.

## 📜 License

Please refer to the project's license file for usage terms and conditions.
