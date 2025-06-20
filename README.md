# Spring AI - Evaluator Optimizer Workflow Pattern

This repository demonstrates the **Spring AI Evaluator Optimizer Workflow Pattern**, a powerful self-improving AI system that creates high-quality output through iterative refinement. The pattern uses two specialized LLM roles - a "Generator" (Writer) to create initial content and an "Evaluator" (Editor) to critique and guide improvements until the output meets predefined quality standards.

📖 **Dive Deeper**: For a complete walkthrough, detailed explanations of the Evaluator Optimizer Workflow pattern, and step-by-step instructions for building the example application, read our comprehensive blog post.<br>
👉 [Spring AI Evaluator Optimizer Pattern: Building Smarter AI That Gets Better With Each Try](https://bootcamptoprod.com/spring-ai-evaluator-optimizer-pattern-guide/)

---

## 📦 Environment Variables

Make sure to provide these Java environment variables when running the application:

- `GEMINI_API_KEY`: Your Google Gemini API key.

---

## About This Project

This project implements a **Smart Email Generator** as a real-world example of the Spring AI Evaluator Optimizer Workflow. It showcases how to:

*   Set up a Spring Boot application with Spring AI and Google Gemini integration.
*   Implement the `EmailEvaluatorOptimizer` workflow with recursive improvement loops.
*   Create specialized AI personas through carefully crafted system prompts for Writer and Editor roles.
*   Build a complete REST API for professional email generation with quality assurance.
*   Handle iterative refinement with safety limits and comprehensive error handling.

The application generates professional emails (job follow-ups, raise requests, etc.) that are automatically refined through multiple rounds of AI-powered editing until they meet strict quality criteria, including a 80-word limit for conciseness.

---

**Learn More**: This is part of our Spring AI Agentic Workflow series. Check out our other workflow patterns:
- [Chain Workflow Pattern](https://bootcamptoprod.com/spring-ai-chain-workflow-guide/)
- [Parallelization Workflow Pattern](https://bootcamptoprod.com/spring-ai-parallelization-workflow-guide/)
- [Routing Workflow Pattern](https://bootcamptoprod.com/spring-ai-routing-workflow-guide/)
- [Orchestrator-Workers Workflow Pattern](https://bootcamptoprod.com/spring-ai-orchestrator-workers-workflow-guide/)