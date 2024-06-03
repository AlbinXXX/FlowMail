// Function to initialize SSE and handle events
export function initializeSSE(domain, firstName, lastName) {
    console.log(`Initializing SSE with domain: ${domain}, firstName: ${firstName}, lastName: ${lastName}`);

    const progressBar = document.getElementById('progressBar');
    const log = document.getElementById('log');

    // Establish SSE connection with dynamic query parameters
    const eventSource = new EventSource(`/stream-emitter?domain=${encodeURIComponent(domain)}&firstName=${encodeURIComponent(firstName)}&lastName=${encodeURIComponent(lastName)}`);

    eventSource.addEventListener('progress', function(event) {
        const data = JSON.parse(event.data);
        const { current, total, email, message } = data;

        // Update progress bar
        const percentage = Math.round((current / total) * 100);
        progressBar.style.width = percentage + '%';
        progressBar.textContent = percentage + '%';

        // Append log entry
        const logEntry = document.createElement('div');
        logEntry.classList.add('log-entry');
        logEntry.textContent = `Checking: ${email} - ${message}`;
        log.appendChild(logEntry);

        // Scroll to the bottom
        log.scrollTop = log.scrollHeight;
    });

    eventSource.addEventListener('complete', function(event) {
        const foundEmails = JSON.parse(event.data);
        console.log(`Email validation complete. Found emails: ${foundEmails.join(', ')}`);

        // Append final result
        if (foundEmails.length > 0) {
            const logEntry = document.createElement('div');
            logEntry.classList.add('log-entry');
            logEntry.innerHTML = `<strong>Found Emails:</strong><br>${foundEmails.join('<br>')}`;
            log.appendChild(logEntry);
        } else {
            const logEntry = document.createElement('div');
            logEntry.classList.add('log-entry');
            logEntry.innerHTML = `<strong>No valid emails found.</strong>`;
            log.appendChild(logEntry);
        }

        // Close the SSE connection
        eventSource.close();
    });

    eventSource.onerror = function(err) {
        console.error("EventSource failed:", err);
        const logEntry = document.createElement('div');
        logEntry.classList.add('log-entry');
        logEntry.innerHTML = `<strong>Error occurred during email validation.</strong>`;
        log.appendChild(logEntry);
        eventSource.close();
    };
}
