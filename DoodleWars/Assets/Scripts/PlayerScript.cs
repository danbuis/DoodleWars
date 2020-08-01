using System;
using UnityEngine;

public class PlayerScript : MonoBehaviour
{
    public float maxSpeed = 1f;
    public float acceleration = 0.02f;
    public float rotationSpeed = 20f;
    private float currentSpeed = 0;
    private float angleAdjustment = 0;
    private float speedAdjustment = 0;


    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if(Input.GetAxisRaw("Horizontal") != 0)
        {
            angleAdjustment = Input.GetAxisRaw("Horizontal") * rotationSpeed * Time.deltaTime;
        }
        else
        {
            angleAdjustment = 0;
        }

        if (Input.GetAxisRaw("Vertical") != 0)
        {
            speedAdjustment = Input.GetAxisRaw("Vertical") * acceleration * Time.deltaTime;
            currentSpeed = currentSpeed + speedAdjustment;

            if (currentSpeed > maxSpeed)
            {
                currentSpeed = maxSpeed;
            }
            else if (currentSpeed < 0)
            {
                currentSpeed = 0;
            }
        }

        Vector3 movement = new Vector3(0, currentSpeed, 0);

        gameObject.transform.Translate(movement);
        gameObject.transform.Rotate(0, 0, -angleAdjustment);

    }
}
