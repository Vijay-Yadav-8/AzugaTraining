import { MongoClient } from "mongodb";


const uri = "mongodb://localhost:27017";

const client = new MongoClient(uri);

async function run() {
  try {
    const database = client.db("museumdb");
    const movies = database.collection("museum");
    const query = { objectID: {$eq : 1} };

    const options = {
      sort: { "objectID": 1 }
    };
    const cursor = movies.find(query, options);
    if ((await cursor.count()) === 0) {
      console.log("No documents found!");
    }
    await cursor.forEach(console.dir);
  } finally {
    await client.close();
  }
}
run().catch(console.dir);


async function insertOneRec() {
  try {
    const database = client.db("museumdb");
    const haiku = database.collection("museum");
    const doc = {
    objectID: 1,
    isHighlight: false,
    accessionNumber: '13.120.36',
    accessionYear: '1913',
    isPublicDomain: true,
    primaryImage: 'https://images.metmuseum.org/CRDImages/ad/original/17468.jpg',
    primaryImageSmall: 'https://images.metmuseum.org/CRDImages/ad/web-large/17468.jpg',
    additionalImages: [],
    constituents: null,
    department: 'The American Wing',
    objectName: 'Mug',
    title: 'Mug',
    culture: '',
    period: '',
    dynasty: '',
    reign: '',
    portfolio: '',
    artistRole: '',
    artistPrefix: '',
    artistDisplayName: '',
    artistDisplayBio: '',
    artistSuffix: '',
    artistAlphaSort: '',
    artistNationality: '',
    artistBeginDate: '',
    artistEndDate: '',
    artistGender: '',
    artistWikidata_URL: '',
    artistULAN_URL: '',
    objectDate: '18th century',
    objectBeginDate: 1700,
    objectEndDate: 1800,
    medium: 'Non-lead glass with enamel decoration',
    dimensions: 'H. 6 1/2 in. (16.5 cm)',
    measurements: [
      {
        elementName: 'Overall',
        elementDescription: null,
        elementMeasurements: [Object]
      }
    ],
    creditLine: 'Gift of Frederick W. Hunter, 1913',
    geographyType: 'Made in',
    city: '',
    state: '',
    county: '',
    country: 'Czech Republic',
    region: 'Bohemia',
    subregion: '',
    locale: '',
    locus: '',
    excavation: '',
    river: '',
    classification: '',
    rightsAndReproduction: '',
    linkResource: '',
    metadataDate: '2021-04-06T04:41:04.967Z',
    repository: 'Metropolitan Museum of Art, New York, NY',
    objectURL: 'https://www.metmuseum.org/art/collection/search/5276',
    tags: null,
    objectWikidata_URL: '',
    isTimelineWork: false,
    GalleryNumber: '774',
    new1: [ { hello: 'hi', age: 22 } ]
  }
    const result = await haiku.insertOne(doc);
    console.log(`A document was inserted with the _id: ${result.insertedId}`);
  } finally {
    await client.close();
  }
}
// insertOneRec().catch(console.dir);

async function updateOneRec() {
  try {
    const database = client.db("museumdb");
    const movies = database.collection("museum");
    const filter = { objectID: 1};
    const options = { upsert: true };
    const updateDoc = {
      $set: {
        isHighlight: true
      },
    };
    const result = await movies.updateOne(filter, updateDoc, options);
    console.log(
      `${result.matchedCount} document(s) matched the filter, updated ${result.modifiedCount} document(s)`,
    );
  } finally {
    await client.close();
  }
}
// updateOneRec().catch(console.dir);

async function deleteOneRec() {
  try {
    const database = client.db("museumdb");
    const movies = database.collection("museum");
    const query = { objectID: 1 };
    const result = await movies.deleteOne(query);
    if (result.deletedCount === 1) {
      console.log("Successfully deleted one document.");
    } else {
      console.log("No documents matched the query. Deleted 0 documents.");
    }
  } finally {
    await client.close();
  }
}
// deleteOneRec().catch(console.dir);
