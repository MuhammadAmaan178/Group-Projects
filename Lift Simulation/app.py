import random
import time
import threading
from flask import Flask, render_template, jsonify, request

app = Flask(__name__)

# Lift state initialization
lift_floors = [1, 1, 1]  # Lifts initially at floor 1
lift_people_count = [0, 0, 0]  # No people in lifts
MAX_PEOPLE = 7  # Maximum number of people per lift
MAX_FLOORS = 8  # Maximum number of floors

# Function to simulate the lift movement
def move_lift(lift_id, start_floor, requested_floor, callback):
    # Move to the requested floor first
    direction = "up" if start_floor < requested_floor else "down"
    if direction == "up":
        for floor in range(start_floor + 1, requested_floor + 1):
            lift_floors[lift_id] = floor
            time.sleep(3)  # Simulate moving floor by floor
            callback(lift_id)  # Send status update to frontend

    else:
        for floor in range(start_floor - 1, requested_floor - 1, -1):
            lift_floors[lift_id] = floor
            time.sleep(3)  # Simulate moving floor by floor
            callback(lift_id)  # Send status update to frontend

    # After reaching requested floor, randomly choose the next floor
    random_next_floor = random.choice([i for i in range(1, MAX_FLOORS + 1) if i != requested_floor])
    time.sleep(2)  # Simulate a brief stop

    # Move to the randomly selected floor
    if random_next_floor > requested_floor:
        for floor in range(requested_floor + 1, random_next_floor + 1):
            lift_floors[lift_id] = floor
            time.sleep(3)
            callback(lift_id)
    else:
        for floor in range(requested_floor - 1, random_next_floor - 1, -1):
            lift_floors[lift_id] = floor
            time.sleep(3)
            callback(lift_id)

    # Reset the number of people (passengers leave)
    lift_people_count[lift_id] = 0
    callback(lift_id)  # Notify that lift has reached random floor and people have exited

@app.route('/')
def index():
    return render_template('index.html')

@app.route('/call_lift', methods=['POST'])
def call_lift():
    data = request.json
    requested_floor = int(data['requested_floor'])
    people_count = int(data['people_count'])

    # Find nearest lift to call
    min_diff = float('inf')
    selected_lift = -1
    for i in range(3):
        diff = abs(lift_floors[i] - requested_floor)
        if diff < min_diff:
            min_diff = diff
            selected_lift = i
    
    # Check if lift can accommodate the people
    if lift_people_count[selected_lift] + people_count <= MAX_PEOPLE:
        lift_people_count[selected_lift] += people_count
        # Start the lift movement asynchronously
        move_lift_thread = threading.Thread(target=move_lift, args=(selected_lift, lift_floors[selected_lift], requested_floor, lift_callback))
        move_lift_thread.start()
        
        return jsonify({
            "status": "Lift moving",
            "lift_id": selected_lift,
            "current_floor": lift_floors[selected_lift],
            "people_count": lift_people_count[selected_lift]
        })
    
    else:
        return jsonify({
            "status": "Lift full",
            "lift_id": selected_lift,
            "current_floor": lift_floors[selected_lift],
            "people_count": lift_people_count[selected_lift]
        })

# Callback function to notify the frontend with lift movement status
def lift_callback(lift_id):
    # Send updates to the frontend, or you could use websockets for real-time updates.
    pass

@app.route('/get_lift_status', methods=['GET'])
def get_lift_status():
    return jsonify({
        "lift_floors": lift_floors,
        "lift_people_count": lift_people_count,
        "max_people": MAX_PEOPLE,
        "max_floors": MAX_FLOORS
    })

if __name__ == '__main__':
    app.run(debug=True)
