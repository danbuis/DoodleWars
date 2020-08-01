using System;
using UnityEngine;

public class PlayerScript : MonoBehaviour
{
    public float maxSpeed = 1f;
    public float acceleration = 0.02f;
    public float rotationSpeed = 20f;
    private float currentSpeed = 0;
    private float angle = 0;
    private float angleAdjustment = 0;


    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        if(Input.GetAxisRaw("Horizontal") != 0)
        {
            Debug.Log("horizontal input : " + Input.GetAxisRaw("Horizontal"));
            angleAdjustment = Input.GetAxisRaw("Horizontal") * rotationSpeed * Time.deltaTime;
            Debug.Log("Adjustment : " + angleAdjustment);
            angle = angle + angleAdjustment;
        }
        else
        {
            angleAdjustment = 0;
        }

        if (Input.GetAxisRaw("Vertical") != 0)
        {
            float speedAdjustment = Input.GetAxisRaw("Vertical") * acceleration * Time.deltaTime;
            float newSpeed = currentSpeed + speedAdjustment;

            if (newSpeed > maxSpeed)
            {
                currentSpeed = maxSpeed;
            }
            else if (newSpeed < 0)
            {
                currentSpeed = 0;
            }
            else currentSpeed = newSpeed;
        }

        Debug.Log("Angle : " + angle);

        //convert angle to radians
        float angleInRad = angle * (float)Math.PI / 180f;

        Debug.Log("Angle in radians : " + angleInRad);
        Debug.Log("Current Speed : " + currentSpeed);

        Debug.Log("value 1 : " + ((float)Math.Sin(angleInRad)));
        Debug.Log("value 2 : " + ((float)Math.Cos(angleInRad)));

        Vector3 movement = new Vector3(currentSpeed * (float)Math.Sin(angleInRad), currentSpeed * (float)Math.Cos(angleInRad), 0);
        Debug.Log(movement);
        gameObject.transform.Translate(movement);
        gameObject.transform.Rotate(0, 0, -angleAdjustment);
        //gameObject.transform.localEulerAngles = new Vector3(0f, 0f, angle);
    
        /*float localRotation = gameObject.transform.localRotation.z;
        Debug.Log("LocalRotation : "+localRotation);
        if (localRotation != angle)
        {
            gameObject.transform.Rotate(0, 0, localRotation - angle);
        }*/
    }
}
