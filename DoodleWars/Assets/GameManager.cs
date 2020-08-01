using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameManager : MonoBehaviour
{
    public int mark1Limit = 60;
    public GameObject mark1;

    public int mark2Limit = 90;
    public GameObject mark2;

    public int eraser1Limit = 40;
    public GameObject eraser1;

    public int wrinkle1Limit = 80;
    public GameObject wrinkle1;

    public Rect playArea;

    void Start()
    {
        Vector3 spawnLocation;
        for(int a=0; a<mark1Limit; a++)
        {
            spawnLocation = new Vector3(Random.Range(playArea.x, playArea.width + playArea.x), Random.Range(playArea.y, playArea.height + playArea.y), 0);
            GameObject newDetritus = Instantiate(mark1, spawnLocation, Quaternion.identity);
        }

        for (int b = 0; b < mark2Limit; b++)
        {
            spawnLocation = new Vector3(Random.Range(playArea.x, playArea.width + playArea.x), Random.Range(playArea.y, playArea.height + playArea.y), 0);
            GameObject newDetritus = Instantiate(mark2, spawnLocation, Quaternion.identity);
        }

        for (int c = 0; c < eraser1Limit; c++)
        {
            spawnLocation = new Vector3(Random.Range(playArea.x, playArea.width + playArea.x), Random.Range(playArea.y, playArea.height + playArea.y), 0);
            GameObject newDetritus = Instantiate(eraser1, spawnLocation, Quaternion.identity);
        }

        for (int d = 0; d < wrinkle1Limit; d++)
        {
            spawnLocation = new Vector3(Random.Range(playArea.x, playArea.width + playArea.x), Random.Range(playArea.y, playArea.height + playArea.y), 0);
            GameObject newDetritus = Instantiate(wrinkle1, spawnLocation, Quaternion.identity);
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
