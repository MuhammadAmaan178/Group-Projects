document.getElementById('call-lift-button').addEventListener('click', function() {
    const requestedFloor = document.getElementById('requested-floor').value;
    const peopleCount = document.getElementById('people-count').value;
    
    if (requestedFloor && peopleCount) {
        fetch('/call_lift', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ requested_floor: requestedFloor, people_count: peopleCount })
        })
        .then(response => response.json())
        .then(data => {
            if (data.status === "Lift moving") {
                document.getElementById('status-message').textContent = `Lift ${data.lift_id + 1} is moving to floor ${requestedFloor}.`;
                updateLiftStatus();
            } else {
                document.getElementById('status-message').textContent = `Lift ${data.lift_id + 1} is full. Try another lift.`;
            }
        })
        .catch(error => console.error('Error:', error));
    }
});

// Periodically update the lift status
function updateLiftStatus() {
    fetch('/get_lift_status')
        .then(response => response.json())
        .then(data => {
            document.getElementById('lift-1-floor').textContent = data.lift_floors[0];
            document.getElementById('lift-1-people').textContent = data.lift_people_count[0];
            document.getElementById('lift-2-floor').textContent = data.lift_floors[1];
            document.getElementById('lift-2-people').textContent = data.lift_people_count[1];
            document.getElementById('lift-3-floor').textContent = data.lift_floors[2];
            document.getElementById('lift-3-people').textContent = data.lift_people_count[2];
        });
}

// Initial lift status update
updateLiftStatus();
setInterval(updateLiftStatus, 3000); // Update every 3 seconds
